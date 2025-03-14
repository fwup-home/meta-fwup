inherit image-artifact-names

# Enables firmware authentication using public/private key pair.
FWUP_SIGN_ENABLE ?= "0"
FWUP_PRIVATE_KEY_FILE ?= ""
FWUP_PUBLIC_KEY_FILE ?= ""

# This class relies on wic image class in order to create
# images for bootloader and root partition without having to
# dealing with files in this class. Here we are forcing
# the use of a wks template to avoid the user the needed of
# providing one. It adds a small build time overhead but decreases
# the complexity and maintainability.
WKS_FILE ?= "sdimage-template.wks"

IMAGE_INSTALL:append = " fwup"

FWUP_META_PRODUCT ?= "${PN}"
FWUP_META_DESCRIPTION ?= ""
FWUP_META_VERSION ?= "${PV}"
FWUP_META_AUTHOR ?= ""
FWUP_META_PLATFORM ?= "${DISTRO}-${MACHINE}"
FWUP_META_ARCHITECTURE ?= "${TARGET_ARCH}"
FWUP_META_VCS_IDENTIFIER ?= ""
FWUP_META_MISC ?= "${TARGET_VENDOR}"
FWUP_META_CREATION_DATE ?= "${DATETIME}"
FWUP_META_UUID ?= ""

# The FWUPVARS variable is used to define list of bitbake variables used in fwup code
# variables from this list is written to <image>.env file
FWUPVARS ?= "\
    FWUP_META_PRODUCT \
    FWUP_META_DESCRIPTION \
    FWUP_META_VERSION \
    FWUP_META_AUTHOR \
    FWUP_META_PLATFORM \
    FWUP_META_ARCHITECTURE \
    FWUP_META_VCS_IDENTIFIER \
    FWUP_META_CREATION_DATE \
    FWUP_META_MISC \
    FWUP_META_UUID \
"

IMAGE_TYPEDEP:fwup = "ext4 wic"

FWUP_FILE ??= "${IMAGE_BASENAME}.${MACHINE}.fwup"
FWUP_FILES ?= "${FWUP_FILE} ${IMAGE_BASENAME}.fwup ${MACHINE}.fwup"
FWUP_SEARCH_PATH ?= "${THISDIR}:${@':'.join('%s/fwup' % p for p in '${BBPATH}'.split(':'))}:${COREBASE}'.split(':'))}"
FWUP_FULL_PATH = "${@fwup_search(d.getVar('FWUP_FILES').split(), d.getVar('FWUP_SEARCH_PATH')) or ''}"

FWUP_SIGN = "--private-key-file ${FWUP_PRIVATE_KEY_FILE} --public-key-file ${FWUP_PUBLIC_KEY_FILE}"
FWUP_SIGN_VERIFY = "--public-key-file ${FWUP_PUBLIC_KEY_FILE} -V"

def fwup_search(files, search_path):
    for f in files:
        if os.path.isabs(f):
            if os.path.exists(f):
                return f
        else:
            searched = bb.utils.which(search_path, f)
            if searched:
                return searched

IMAGE_CMD:fwup () {
	out="${IMGDEPLOYDIR}/${IMAGE_NAME}"
	build_fwup="${WORKDIR}/build-fwup"
    fwup="${FWUP_FULL_PATH}"
	if [ -z "$fwup" ]; then
		bbfatal "No fwup files from FWUP_FILES were found: ${FWUP_FILES}. Please set FWUP_FILE or FWUP_FILES appropriately."
	fi

    # load additional variables for fwup purposes
    . $build_fwup/fwup.env

    # Create an fwup firmware
    fwup -q -v ${FWUP_SIGN} -c -o "$build_fwup/${IMAGE_BASENAME}.fw" -f "$fwup"
    # Verify if it's ok
    if [ "${FWUP_SIGN_ENABLE}" = "1" ]; then
        fwup -q ${FWUP_SIGN_VERIFY} -i "$build_fwup/${IMAGE_BASENAME}.fw"
    fi
    # And create a fwup image from a fwup firmware file
    fwup -q -v -t complete -a -d "$build_fwup/${IMAGE_BASENAME}.fwup" -i "$build_fwup/${IMAGE_BASENAME}.fw"

	mv "$build_fwup/${IMAGE_BASENAME}.fw" "$out.fw"
    mv "$build_fwup/${IMAGE_BASENAME}.fwup" "$out.fwup"

    cd ${IMGDEPLOYDIR}
    ln -sf ${IMAGE_NAME}.fw ${IMAGE_LINK_NAME}.fw
}

addtask do_image_fwup after do_image_wic

IMAGE_CMD:fwup[vardepsexclude] = "FWUP_FULL_PATH FWUP_FILES TOPDIR"

# Rebuild when the fwup file or vars in FWUPVARS change
USING_FWUP = "${@bb.utils.contains_any('IMAGE_FSTYPES', 'fwup ' + ' '.join('fwup.%s' % c for c in '${CONVERSIONTYPES}'.split()), '1', '', d)}"
FWUP_FILE_CHECKSUM = "${@'${FWUP_FULL_PATH}:%s' % os.path.exists('${FWUP_FULL_PATH}') if '${USING_FWUP}' else ''}"
do_image_fwup[file-checksums] += "${FWUP_FILE_CHECKSUM}"

do_image_fwup[depends] += "fwup-native:do_populate_sysroot"

FWUP_FILE_DEPENDS = "fwup-native"

DEPENDS += "${@ '${FWUP_FILE_DEPENDS}' if d.getVar('USING_FWUP') else '' }"

def get_first_file(globs, root_dir):
    """Return first file found in wildcard globs"""
    import glob
    for g in globs:
        all_files = glob.glob(g, root_dir=root_dir)
        if all_files:
            for f in all_files:
                if not os.path.isdir(f):
                    return f
    return None

def calculate_size_blocks(d, image_file):
    block_size = 512

    data_size_blocks, data_size_rest = divmod(os.stat(image_file).st_size, block_size)
    data_blocks = data_size_blocks + (1 if data_size_rest else 0)
    data_size = data_blocks * block_size

    bb.debug(1, f"the file {image_file} has data_size {data_size} data_size_blocks: {data_size_blocks}, {data_size_rest}")

    return data_blocks

python do_fwup_conf () {
    fwupvars = d.getVar('FWUPVARS')
    if not fwupvars:
        bb.fatal("FWUPVARS not defined")
        return

    build_wic = os.path.join(d.getVar('WORKDIR'), 'build-wic')
    build_fwup = os.path.join(d.getVar('WORKDIR'), 'build-fwup')

    wks_file = d.getVar('WKS_FULL_PATH')
    wks_file_basename = os.path.basename(wks_file).rsplit('.', 1)[0]

    glob_bootloader = "%s-*-*.p1" % wks_file_basename
    glob_rootfs = "%s-*-*.p2" % wks_file_basename

    bootloader = get_first_file([glob_bootloader], build_wic)
    if not bootloader:
        bb.fatal("no glob %s in %s: '%s'" % (glob_bootloader, build_wic, bootloader))

    rootfs = get_first_file([glob_rootfs], build_wic)
    if not rootfs:
        bb.fatal("no glob %s in %s: '%s'" % (glob_rootfs, build_wic, rootfs))

    bootloader_img = os.path.join(build_wic, bootloader)
    rootfs_img = os.path.join(build_wic, rootfs)

    bb.debug(1, f"boot {bootloader_img} img {rootfs_img}")

    fwup_env_filename = os.path.join(build_fwup, "fwup.env")

    fwup_resources = {
        "FWUP_BOOTLOADER_IMG": bootloader_img,
        "FWUP_BOOTLOADER_IMG_BLOCKS": calculate_size_blocks(d, bootloader_img),
        "FWUP_ROOTFS_IMG": rootfs_img,
        "FWUP_ROOTFS_IMG_BLOCKS": calculate_size_blocks(d, rootfs_img),

        "DEPLOY_DIR_IMAGE": d.getVar('DEPLOY_DIR_IMAGE')
    }

    with open(fwup_env_filename, 'w') as conf:
        for var in fwupvars.split():
            value = d.getVar(var)
            if value:
                #final_var = var.replace("FWUP_", "").lower().replace("_", "-")
                conf.write('export %s="%s"\n' % (var, value.strip()))

        for resource, imgfile in fwup_resources.items():
            conf.write('export %s="%s"\n' % (resource, imgfile))

    conf.close()
}

python do_check_key_pair() {
    priv_key = d.getVar('FWUP_PRIVATE_KEY_FILE')
    pub_key = d.getVar('FWUP_PUBLIC_KEY_FILE')
    sign = d.getVar('FWUP_SIGN_ENABLE') == '1'

    if sign and not os.path.isfile(priv_key):
        bb.fatal(f"Private key {priv_key} not found")
    if sign and not os.path.isfile(pub_key):
        bb.fatal(f"Public key {pub_key} not found")
}

addtask do_fwup_conf after do_image_wic before do_image_fwup
do_fwup_conf[cleandirs] = "${WORKDIR}/build-fwup"
do_fwup_conf[vardeps] += "${FWUPVARS}"
do_fwup_conf[vardepsexclude] += "FWUP_META_CREATION_DATE"

addtask do_check_key_pair after do_image_wic before do_image_fwup
do_check_key_pair[vardeps] += "FWUP_SIGN_ENABLE FWUP_PRIVATE_KEY_FILE FWUP_PUBLIC_KEY_FILE"

IMAGE_TYPES += " fwup"

CONVERSIONTYPES:append = " fwup.qcow2 fwup.bmap"

CONVERSION_CMD:fwup.qcow2 = "qemu-img convert -O qcow2 ${IMAGE_NAME}.${type} ${IMAGE_NAME}.${type}.qcow2"
CONVERSION_CMD:fwup.bmap = "bmaptool create ${IMAGE_NAME}.${type} -o ${IMAGE_NAME}.${type}.bmap"

CONVERSION_DEPENDS_fwup.qcow2 = "qemu-system-native"
CONVERSION_DEPENDS_fwup.bmap = "bmaptool-native"

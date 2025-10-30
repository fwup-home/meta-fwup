SUMMARY = "Create u-boot environment file for fwup"
DESCRIPTION = "Create u-boot environment file for fwup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# if fw_env.config file is provided, it will be installed into ${sysconfigdir}
# - see u-boot.inc file
SRC_URI = " \
    file://uboot.txt.env \
"

S = "${UNPACKDIR}"

inherit nopackages deploy

DEPENDS = "u-boot-mkenvimage-native"

do_fetch[noexec] = "1"
do_patch[noexec] = "1"
#do_compile[noexec] = "1"
do_install[noexec] = "1"
deltask do_populate_sysroot

do_deploy[depends] += "\
    u-boot-mkenvimage-native:do_populate_sysroot \
    "

UBOOT_MKENVIMAGE = "uboot-mkenvimage"

do_compile() {
    ${UBOOT_MKENVIMAGE} -s 0x20000 -o ${B}/uboot.env ${UNPACKDIR}/uboot.txt.env
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/uboot.env ${DEPLOYDIR}
}

addtask deploy after do_install

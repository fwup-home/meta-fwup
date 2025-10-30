# Based on https://github.com/rauc/meta-rauc-community

DESCRIPTION = "Create additional grubenv for booting with fwup"
LICENSE = "MIT"

inherit nopackages deploy

do_fetch[noexec] = "1"
do_patch[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"
deltask do_populate_sysroot

do_deploy[depends] += "\
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    grub-native:do_populate_sysroot \
    u-boot-fwup:do_deploy \
    "

do_deploy () {
    MKDOSFS_EXTRAOPTS="-S 512"

    grub-editenv ${WORKDIR}/grubenv_a create
    grub-editenv ${WORKDIR}/grubenv_a set boot=0
    grub-editenv ${WORKDIR}/grubenv_a set validated=0
    grub-editenv ${WORKDIR}/grubenv_a set booted_once=0

    grub-editenv ${WORKDIR}/grubenv_b create
    grub-editenv ${WORKDIR}/grubenv_b set boot=1
    grub-editenv ${WORKDIR}/grubenv_b set validated=0
    grub-editenv ${WORKDIR}/grubenv_b set booted_once=0

    cp ${WORKDIR}/grubenv_a ${WORKDIR}/grubenv_a_valid
    grub-editenv ${WORKDIR}/grubenv_a_valid set booted_once=1
    grub-editenv ${WORKDIR}/grubenv_a_valid set validated=1

    cp ${WORKDIR}/grubenv_b ${WORKDIR}/grubenv_b_valid
    grub-editenv ${WORKDIR}/grubenv_b_valid set booted_once=1
    grub-editenv ${WORKDIR}/grubenv_b_valid set validated=1

    GRUBENV_IMG="${WORKDIR}/grubenv.img"

    rm -f ${GRUBENV_IMG}

    mkdosfs -n "BOOTENV" ${MKDOSFS_EXTRAOPTS} -C ${GRUBENV_IMG} 64
    chmod 644 ${GRUBENV_IMG}

    mv ${GRUBENV_IMG} ${DEPLOYDIR}/
    mv ${WORKDIR}/grubenv_a ${DEPLOYDIR}/
    mv ${WORKDIR}/grubenv_a_valid ${DEPLOYDIR}/
    mv ${WORKDIR}/grubenv_b ${DEPLOYDIR}/
}

do_deploy[cleandirs] += "${WORKDIR}/efi-boot"

addtask deploy after do_install

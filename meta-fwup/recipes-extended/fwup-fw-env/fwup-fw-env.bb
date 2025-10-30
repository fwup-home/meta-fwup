SUMMARY = "Configuration file for fw_(printenv/setenv) utility"
DESCRIPTION = "fwup tool does not use fw_(printenv/setenv) utility directly. \
               However, it is necessary to have it installed when using nerves-hub_link \
               based applications."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

COMPATIBLE_MACHINE = "^(rpi|qemuall)$"

SRC_URI = " \
    file://fw_env.config \
"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${UNPACKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

CONFFILES:${PN} += "${sysconfdir}/fw_env.config"

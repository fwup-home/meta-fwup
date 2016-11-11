SUMMARY = "Small configuration file parser library for C"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/martinh/libconfuse"
SECTION = "devel"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef0220292b0cce0a53f5faff0d1f102a"

DEPENDS = "flex-native gettext"

SRC_URI = "https://github.com/martinh/libconfuse/releases/download/v${PV}/confuse-${PV}.tar.gz"

SRC_URI[md5sum] = "bf03099ef213647451c70e54ad4b6e81"
SRC_URI[sha256sum] = "f1f326d9443103036d19c32d3f3efec3a85c3b081d99534463668d29992c4648"

inherit autotools-brokensep lib_package gettext

BBCLASSEXTEND = "native nativesdk"

S = "${WORKDIR}/confuse-${PV}"

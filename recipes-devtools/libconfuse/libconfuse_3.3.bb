SUMMARY = "Small configuration file parser library for C"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/martinh/libconfuse"
SECTION = "devel"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef0220292b0cce0a53f5faff0d1f102a"

DEPENDS = "flex-native gettext"

SRC_URI = "https://github.com/martinh/libconfuse/releases/download/v${PV}/confuse-${PV}.tar.gz"

SRC_URI[md5sum] = "f590564c6ea4879db0c8f692bf12f42a"
SRC_URI[sha256sum] = "3a59ded20bc652eaa8e6261ab46f7e483bc13dad79263c15af42ecbb329707b8"

inherit autotools-brokensep lib_package gettext

BBCLASSEXTEND = "native nativesdk"

S = "${WORKDIR}/confuse-${PV}"

SUMMARY = "Configurable embedded Linux firmware update creator and runner"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/fhunleth/fwup"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "libconfuse libarchive libsodium zlib pkgconfig-native"

SRC_URI = "https://github.com/fhunleth/fwup/releases/download/v${PV}/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "81ccbe8c2b31a0714e1587a857aa22f1"
SRC_URI[sha256sum] = "9ad389c96429e6c29d9c45d145de0e7c04968794864a872ce939933e0ab5f4bd"

inherit autotools lib_package pkgconfig

#BBCLASSEXTEND = "native nativesdk"



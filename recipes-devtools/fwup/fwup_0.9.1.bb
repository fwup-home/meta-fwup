SUMMARY = "Configurable embedded Linux firmware update creator and runner"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/fhunleth/fwup"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "libconfuse libarchive zlib"

SRC_URI = "https://github.com/fhunleth/fwup/releases/download/v${PV}/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "abdb5ec5aa8b0693284a6d212b1d1141"
SRC_URI[sha256sum] = "6c2727be22761b7be2ec47c084a9c6e8d0b701bae14f09dc45faaaadb3d78c23"

inherit autotools

BBCLASSEXTEND = "native nativesdk"

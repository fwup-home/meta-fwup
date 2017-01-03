SUMMARY = "Configurable embedded Linux firmware update creator and runner"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/fhunleth/fwup"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "libconfuse libarchive libsodium zlib pkgconfig-native"

SRC_URI = "https://github.com/fhunleth/fwup/releases/download/v${PV}/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "be6b31da55abea3d43ca9ea19fa640ff"
SRC_URI[sha256sum] = "64a2967279eb9a11ade9350e53898cf49318e11f26d68f3f3ce28ed9ad7f455c"

inherit autotools lib_package pkgconfig
FILES_${PN} += "${datadir}/bash-completion/completions/fwup \
               ${bindir}/fwup \
"
PACKAGES = "${PN}-dev ${PN}-dbg ${PN}"
BBCLASSEXTEND = "native nativesdk"



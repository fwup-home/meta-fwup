SUMMARY = "Configurable embedded Linux firmware update creator and runner"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/fhunleth/fwup"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "libconfuse libarchive libsodium zlib pkgconfig-native"

SRC_URI = "https://github.com/fhunleth/fwup/releases/download/v${PV}/${BPN}-${PV}.tar.gz"

SRC_URI[sha256sum] = "20302dc96cef88438034e15551e178bb0652c28d99aa7ca5260100cb3bebbc2a"

inherit autotools lib_package pkgconfig
FILES_${PN} += "${datadir}/bash-completion/completions/fwup \
               ${bindir}/fwup \
"
PACKAGES = "${PN}-dev ${PN}-dbg ${PN}"
BBCLASSEXTEND = "native nativesdk"



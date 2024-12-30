SUMMARY = "Configurable embedded Linux firmware update creator and runner"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/fhunleth/fwup"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
DEPENDS = "libconfuse libarchive libsodium zlib pkgconfig-native"

SRC_URI = "git://github.com/fhunleth/fwup.git;protocol=https;branch=main;"

# Modify these as desired
PV = "1.12.0"
SRCREV = "3dd1798815c206a3c05e8cfbd51de99b2f1dd930"

S = "${WORKDIR}/git"

CFLAGS:prepend = "-I${S} "

inherit autotools lib_package pkgconfig
FILES:${PN} += "${datadir}/bash-completion/completions/fwup \
               ${bindir}/fwup \
"

FILES:${PN}-img2fwup = "${bindir}/img2fwup"

PACKAGES = "${PN}-dev ${PN}-dbg ${PN}-img2fwup ${PN}"
BBCLASSEXTEND = "native nativesdk"

do_configure:append () {
  ln -s ${S}/src/fwup.h2m ${B}/src/fwup.h2m
}

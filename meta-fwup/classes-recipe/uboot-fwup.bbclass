DEPENDS:append = "u-boot-mkenvimage-native"

do_compile:append() {
    uboot-mkenvimage -s 0x20000 -o ${B}/uboot.env ${UNPACKDIR}/uboot.txt.env
}

do_deploy:append() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/uboot.env ${DEPLOYDIR}
}
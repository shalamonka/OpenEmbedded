require libtool_${PV}.bb

PACKAGES = ""

SRC_URI_append = " file://prefix.patch \
                   file://cross.patch \
                 "

inherit nativesdk
do_configure_prepend () {
        # Remove any existing libtool m4 since old stale versions would break
        # any upgrade
        rm -f ${STAGING_DATADIR}/aclocal/libtool.m4
        rm -f ${STAGING_DATADIR}/aclocal/lt*.m4
}

do_install () {
        autotools_do_install
        install -d ${D}${bindir}/
        install -m 0755 ${HOST_SYS}-libtool ${D}${bindir}/
}

SYSROOT_PREPROCESS_FUNCS += "libtoolnativesdk_sysroot_preprocess"

libtoolnativesdk_sysroot_preprocess () {
        install -d ${SYSROOT_DESTDIR}${STAGING_BINDIR_CROSS}/
        install -m 755 ${D}${bindir}/${HOST_SYS}-libtool ${SYSROOT_DESTDIR}${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool
}
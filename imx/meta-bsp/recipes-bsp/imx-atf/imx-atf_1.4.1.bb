# Copyright 2017 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit fsl-eula-unpack pkgconfig deploy

PV = "1.4.1+git${SRCPV}"

ATF_SRC ?= "git://source.codeaurora.org/external/imx/imx-atf.git;protocol=https"
ATF_BRANCH = "imx_4.9.51_imx8_beta2"

SRC_URI = "${ATF_SRC};branch=${ATF_BRANCH}"
SRCREV = "684a05c63eb79ccdfe3626815fc45218c076a1c4"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"

SOC_ATF ?= "imx8qm"
SOC_ATF_mx8qm = "imx8qm"
SOC_ATF_mx8qxp = "imx8qxp"
SOC_ATF_mx8mq = "imx8mq"

SYSROOT_DIRS += "/boot"

do_compile () {
    export CROSS_COMPILE="${TARGET_PREFIX}"
    cd ${S}
    # Clear LDFLAGS to avoid the option -Wl recognize issue
    unset LDFLAGS

    echo "-> Build ${SOC_ATF} bl31.bin"
    # Set BUIL_STRING with the revision info
    BUILD_STRING=""
    if [ -e ${S}/.revision ]; then
        cur_rev=`cat ${S}/.revision`
        echo " Current revision is ${cur_rev} ."
        BUILD_STRING="BUILD_STRING=${cur_rev}"
    else
        echo " No .revision found! "
    fi
    oe_runmake clean PLAT=${SOC_ATF}
    oe_runmake ${BUILD_STRING} PLAT=${SOC_ATF} bl31

    unset CROSS_COMPILE
}

do_install () {
    install -d ${D}/boot
    install -m 0644 ${S}/build/${SOC_ATF}/release/bl31.bin ${D}/boot/bl31-${SOC_ATF}.bin
}

do_deploy () {
    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
    install -m 0644 ${S}/build/${SOC_ATF}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${SOC_ATF}.bin
}

addtask deploy before do_install after do_compile

FILES_${PN} = "/boot"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8)"

include recipes-multimedia/gstreamer/gstreamer1.0-rtsp-server.inc

FILESEXTRAPATHS_prepend := "${BSPDIR}/sources/poky/meta/recipes-multimedia/gstreamer/${PN}:"
FILESEXTRAPATHS_prepend := "${BSPDIR}/sources/poky/meta/recipes-multimedia/gstreamer/files:"

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

SRC_URI[md5sum] = "022757cab183f5b970086e9101c60a98"
SRC_URI[sha256sum] = "d8ba9264e8ae6e440293328e759e40456f161aa66077b3143dd07581136190b3"

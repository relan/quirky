source = icon.svg
mipmap = src/main/res/mipmap
densities = mdpi hdpi xhdpi xxhdpi xxxhdpi

all : $(foreach x, $(densities), $(mipmap)-$(x)/ic_launcher.png)

$(mipmap)-mdpi/ic_launcher.png : $(source)
	inkscape $< -e $@ -w 48

$(mipmap)-hdpi/ic_launcher.png : $(source)
	inkscape $< -e $@ -w 72

$(mipmap)-xhdpi/ic_launcher.png : $(source)
	inkscape $< -e $@ -w 96

$(mipmap)-xxhdpi/ic_launcher.png : $(source)
	inkscape $< -e $@ -w 144

$(mipmap)-xxxhdpi/ic_launcher.png : $(source)
	inkscape $< -e $@ -w 192

.PHONY : all

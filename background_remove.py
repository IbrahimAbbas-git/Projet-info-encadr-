from PIL import Image

def remove_back(img) :
    datas = img.getdata()

    newData = []
    for item in datas:
        # Si le pixel est blanc, le rendre transparent
        if item[:3] == (255, 255, 255):
            newData.append((255, 255, 255, 0))
        else:
            newData.append(item)

    img.putdata(newData)
    return img

img = remove_back(Image.open("bombe.png").convert("RGBA"))
img.save("bombe_transparente.png")
img = remove_back(Image.open("drapeau.png").convert("RGBA"))
img.save("drapeau_transparent.png")

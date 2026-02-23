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

img = remove_back(Image.open("un.png").convert("RGBA"))
img.save("un.png")
img = remove_back(Image.open("deux.png").convert("RGBA"))
img.save("deux.png")
img = remove_back(Image.open("trois.png").convert("RGBA"))
img.save("trois.png")
img = remove_back(Image.open("quatre.png").convert("RGBA"))
img.save("quatre.png")
img = remove_back(Image.open("cinq.png").convert("RGBA"))
img.save("cinq.png")
img = remove_back(Image.open("six.png").convert("RGBA"))
img.save("six.png")
img = remove_back(Image.open("sept.png").convert("RGBA"))
img.save("sept.png")
img = remove_back(Image.open("huit.png").convert("RGBA"))
img.save("huit.png")

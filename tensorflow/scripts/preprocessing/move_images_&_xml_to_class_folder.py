"""
Moves the Images to different folders only with images and the respective xml file, 
all of the same class


arguments:
    XML_DIR: Path to the xml files
"""

import os
import glob
import re
import xml.etree.ElementTree as ET


XML_DIR = './../../data/train/'
IMAGE_FORMAT = 'jpg'


def add_object_to_count(count, object_name):
    try:
        count[object_name] += 1
    except:
        count[object_name] = 1

    return count


def main():
    count = {}

    for xml_file in glob.glob(f'{XML_DIR}*.xml'):
        xml_name = re.split(r'/|\\', xml_file)[-1].split('.')[:-1]

        tree = ET.parse(xml_file)
        root = tree.getroot()

        objects_in_img = {}

        for member in root.findall('object'):
            bndbox = member.find('bndbox')
            object_name = member.find('name').text

            count = add_object_to_count(count, object_name)
            objects_in_img = add_object_to_count(objects_in_img, object_name)


        if len(objects_in_img) >= 2:
            if not os.path.exists('./multiclass/'):
                os.mkdir('./multiclass/')

            os.rename(f'{xml_file[:-4]}.{IMAGE_FORMAT}', './multiclass/{xml_name}.{extension}'
                .format(xml_name='.'.join(xml_name), extension=IMAGE_FORMAT))
            os.rename(xml_file, './multiclass/{xml_name}.xml'.format(xml_name='.'.join(xml_name)))
            
        else:
            object_name = list(objects_in_img.keys())[0]

            if not os.path.exists(f'./{object_name}/'):
                os.mkdir(f'./{object_name}/')


            os.rename(f'{xml_file[:-4]}.{IMAGE_FORMAT}', './{apple}/{xml_name}.{extension}'
                .format(apple=object_name, xml_name='.'.join(xml_name), extension=IMAGE_FORMAT))
            os.rename(xml_file, './{apple}/{xml_name}.xml'.format(apple=object_name, 
                                                                  xml_name='.'.join(xml_name)))
            


if __name__ == '__main__':
    main()

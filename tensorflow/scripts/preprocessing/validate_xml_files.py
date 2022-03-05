"""
Makes sure the 'filename' is correct in the xml file correspond to the right image.
For that is assumes the image as the same name as the xml file.

arguments:
    XML_PATHS: List with paths to the xml files
    IMAGE_FORMAT = Format of the imames ('png', 'jpg', 'jpeg', ...)

"""

import os
import glob
import re
import xml.etree.ElementTree as ET
from tqdm import tqdm

XML_PATHS = ['./../../data/train/', './../../data/test/']
IMAGE_FORMAT = 'jpg'


def change_key_text(tree_root, key, new_text):
    tree_root.find(key).text = new_text


def main():
    for folder in tqdm((XML_PATHS)):
        for xml_file in glob.glob(f'{folder}*.xml'):
            xml_name = re.split(r'/|\\', xml_file)[-1].split('.')[:-1]
            image_name = '{name}.{extension}'.format(name='.'.join(xml_name), extension=IMAGE_FORMAT)
            iamge_path = os.path.join(os.path.abspath(folder), image_name)

            tree = ET.parse(xml_file)
            root = tree.getroot()

            # Change filename name
            change_key_text(root, 'filename', image_name)

            # Change path name
            change_key_text(root, 'path', iamge_path)

            tree.write(xml_file)


if __name__ == '__main__':
    main()

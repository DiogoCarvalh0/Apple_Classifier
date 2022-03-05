"""
Moves the Images to different folders only with images and the respective xml file, 
all of the same class


arguments:
    PATH: Path to the images & xml files
    IMAGE_FORMAT: Image format (jpg, png, ...)
    OUTPUT_PATH: Path to were the folders should be created
"""

import os
from shutil import copyfile
import glob
import re
from tqdm import tqdm
import xml.etree.ElementTree as ET


PATH = './../../data/imgs/'
IMAGE_FORMAT = 'jpg'
OUTPUT_PATH = './../../data/'


def add_object_to_count(count, object_name):
    try:
        count[object_name] += 1
    except:
        count[object_name] = 1

    return count


def find_objects_in_xml(tree_root):
    objects_in_xml = {}
    
    for member in tree_root.findall('object'):
        object_name = member.find('name').text
        objects_in_xml = add_object_to_count(objects_in_xml, object_name)
        
    return objects_in_xml
    

def move_files(save_folder, image_file, xml_file):
    image_name = re.split(r'/|\\', image_file)[-1]
    xml_name = re.split(r'/|\\', xml_file)[-1]
    
    copyfile(image_file, f'{save_folder}/{image_name}')
    copyfile(xml_file, f'{save_folder}/{xml_name}')


def main():
    for xml_file, image_file in tqdm(zip(glob.glob(f'{PATH}*.xml'), glob.glob(f'{PATH}*.{IMAGE_FORMAT}')), total=len(os.listdir(PATH))//2):
        tree = ET.parse(xml_file)
        root = tree.getroot()

        objects_in_img = find_objects_in_xml(root)

        if len(objects_in_img) >= 2:
            if not os.path.exists(f'{OUTPUT_PATH}multiclass/'):
                os.mkdir(f'{OUTPUT_PATH}multiclass/')
                
            move_files(f'{OUTPUT_PATH}multiclass/', image_file, xml_file)
            
        else:
            object_name = list(objects_in_img.keys())[0]

            if not os.path.exists(f'{OUTPUT_PATH}{object_name}/'):
                os.mkdir(f'{OUTPUT_PATH}{object_name}/')
                
            move_files(f'{OUTPUT_PATH}{object_name}/', image_file, xml_file)        


if __name__ == '__main__':
    main()

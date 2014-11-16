#!/usr/bin/env python
import os
from datetime import datetime
import zipfile

if __name__ == '__main__':
    zip_name = "Axelsson-Classon-Roshult-" + datetime.now().strftime('%Y-%m-%d_%H_%M') + ".zip"
    root = os.path.dirname(os.path.realpath(__file__))
    zip_path = os.path.join(root, zip_name)
    zfile = zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED)
    
    folders = ['src', 'test', 'systemtest']

    for folder in folders:
        folder_path = os.path.join(os.path.dirname(os.path.realpath(__file__)), folder)
        rootlen = len(root)
        for base, dirs, files in os.walk(folder_path):
            for file in files:
                fn = os.path.join(os.path.join(root, base), file)
                zfile.write(fn, os.path.join(folder_path, fn[rootlen:]))
    zfile.close()
    print("Finished! Created zip file %s." % zip_path)

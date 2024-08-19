import os

def replace_spaces_in_filenames_and_folders(directory):
    # First, rename all folders to replace spaces with underscores
    for root, dirs, files in os.walk(directory, topdown=False):
        for dir_name in dirs:
            if ' ' in dir_name:
                new_dir_name = dir_name.replace(' ', '_')
                old_dir_path = os.path.join(root, dir_name)
                new_dir_path = os.path.join(root, new_dir_name)
                os.rename(old_dir_path, new_dir_path)
                print(f'Renamed folder: {old_dir_path} -> {new_dir_path}')

    # Then, rename all files to replace spaces with underscores
    for root, dirs, files in os.walk(directory):
        for file in files:
            if ' ' in file:
                new_file_name = file.replace(' ', '_')
                old_file_path = os.path.join(root, file)
                new_file_path = os.path.join(root, new_file_name)
                os.rename(old_file_path, new_file_path)
                print(f'Renamed file: {old_file_path} -> {new_file_path}')

if __name__ == "__main__":
    current_directory = os.getcwd()  # Get the current directory
    replace_spaces_in_filenames_and_folders(current_directory)
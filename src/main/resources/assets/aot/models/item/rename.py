import os

def rename_to_lowercase(path):
    # Get the list of files and folders in the current directory
    items = os.listdir(path)

    for item in items:
        item_path = os.path.join(path, item)
        
        if os.path.isdir(item_path):
            # If the item is a directory, recursively call the function on it
            rename_to_lowercase(item_path)
            
            # Rename the directory to lowercase
            new_name = item.lower()
            new_path = os.path.join(path, new_name)
            os.rename(item_path, new_path)
        else:
            # If the item is a file, rename it to lowercase
            new_name = item.lower()
            new_path = os.path.join(path, new_name)
            os.rename(item_path, new_path)

# Get the current execution directory
execution_directory = os.getcwd()

# Call the function to rename files and folders to lowercase
rename_to_lowercase(execution_directory)

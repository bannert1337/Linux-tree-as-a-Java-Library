package tree.in.java;

import java.io.File;
import java.util.Arrays;

public class Tree {
    public static String getTreeStructure(String directory) {
        Tree tree = new Tree();
        return tree.buildTree(directory);
    }

    private String buildTree(String directory) {
        // if directory is not a directory, throw an exception
        if (!new File(directory).isDirectory()) {
            throw new IllegalArgumentException("The given path is not a directory!");
        }

        return walk(new File(directory), "");
    }

    private String walk(File folder, String prefix) {
        File[] fileList = folder.listFiles();
        if (fileList != null) {
            Arrays.sort(fileList);
        }
        StringBuilder tree = new StringBuilder();

        if (fileList == null) {
            return tree.toString();
        }

        for (File file : fileList) {
            if (file.getName().charAt(0) == '.') {
                continue;
            }

            if (file == fileList[fileList.length - 1]) {
                tree.append("\n").append(prefix).append("└── ").append(file.getName());
                if (file.isDirectory()) {
                    tree.append(walk(file, prefix + "    "));
                }
            } else {
                tree.append("\n").append(prefix).append("├── ").append(file.getName());
                if (file.isDirectory()) {
                    tree.append(walk(file, prefix + "│   "));
                }
            }
        }
        return tree.toString();
    }
}

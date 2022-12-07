package project.structure.to.text;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class FileSystemTree {
    // Create a queue to hold the directories to be processed
    private final Queue<File> directoryQueue = new LinkedList<>();

    // Create a string builder to hold the tree representation in text form
    private final StringBuilder treeBuilder = new StringBuilder();

    public FileSystemTree(File rootDirectory) {
        // Add the root directory to the queue
        directoryQueue.add(rootDirectory);
    }

    public String getTree() {
        // Process the directories in the queue
        while (!directoryQueue.isEmpty()) {
            // Get the next directory from the queue
            File currentDirectory = directoryQueue.poll();
            treeBuilder.append(getDirectoryString(currentDirectory));

            // Get the list of files and directories in the current directory
            File[] files = currentDirectory.listFiles();
            if (files != null) {

                for (File file : files) {
                    if (file.isDirectory()) {
                        // Add any directories to the queue
                        directoryQueue.add(file);
                    }
                }
            }
        }
        return treeBuilder.toString();
    }

    private String getDirectoryString(File directory) {
        // Get the name of the directory
        String directoryName = directory.getName();

        // Count the number of directories in the queue that are children of the current directory
        int childDirectoryCount = 0;
        for (File file : directoryQueue) {
            if (file.getParentFile().equals(directory)) {
                childDirectoryCount++;
            }
        }

        // If the current directory has no children, it is a leaf node, and we print it as such
        if (childDirectoryCount == 0) {
            return "|-- " + directoryName + "\n";

        }

        // Otherwise, the current directory has children, and we need to include branch lines for each child
        StringBuilder directoryStringBuilder = new StringBuilder("+-- " + directoryName + "\n");
        directoryStringBuilder.append("|   ".repeat(Math.max(0, childDirectoryCount)));

        return directoryStringBuilder.toString();
    }
}
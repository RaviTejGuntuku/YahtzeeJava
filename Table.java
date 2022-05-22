class Table {
    private int numberRows;
    private int numberColumns;
    private Object[][] table;
    private String tableTitle;

    public Table(Object[][] table) {
        numberRows = table.length;
        numberColumns = table[0].length;

        this.table = table;
    }

    public Table(Object[][] table, String tableTitle) {
        numberRows = table.length;
        numberColumns = table[0].length;

        this.table = table;
        this.tableTitle = tableTitle;

    }

    public String generateTable() {

        String tableString = "";
        int largestCollectiveLength = 0;
        int[] lStringLengths = new int[numberColumns];

        for (int c = 0; c < numberColumns; c++) {
            int lengthLargestString = 0;
            for (int r = 0; r < numberRows; r++) {
                String currentString = table[r][c].toString();
                if (currentString.length() > lengthLargestString)
                    lengthLargestString = currentString.length();
            }

            largestCollectiveLength += lengthLargestString;
            lStringLengths[c] = lengthLargestString;
        }

        int lineLength = largestCollectiveLength + numberColumns * 4 + numberColumns + 1;

        for (int i = 0; i < lineLength; i++) {
            tableString += "-";
        }
        tableString += "\n";

        for (int r = 0; r < numberRows; r++) {
            for (int c = 0; c < numberColumns; c++) {
                String formattedElement = String.format("|  %-" + lStringLengths[c] + "s  ", table[r][c].toString());
                if (c == numberColumns - 1) {
                    formattedElement += "|";
                }
                tableString += formattedElement;
            }
            tableString += "\n";
        }

        for (int i = 0; i < lineLength; i++) {
            tableString += "-";
        }
        tableString += "\n";

        return tableString;

    }

    public String toString() {
        if (tableTitle != null)
            return tableTitle + "\n" + generateTable();
        else {
            return generateTable();
        }
    }
}
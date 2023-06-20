class Result {
     private int incorrectWord;
    private int correctWord;
    private StringBuilder modifiedText1;
    public Result(int incorrectWord, int correctWord, StringBuilder modifiedText1) {
        super();

        this.incorrectWord =incorrectWord;
        this.correctWord =correctWord;
        this.modifiedText1 = modifiedText1;
    }


    public int getIncorrectword() {
        return incorrectWord;
    }


    public StringBuilder getModifiedText1() {
        return modifiedText1;
    }
}

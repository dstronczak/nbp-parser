package pl.parser.nbp;

public enum ExitCode {
    SUCCESS(0), USER_ERROR(1), PROGRAM_ERROR(2);

    private final int code;

    ExitCode(int code) {
        this.code  = code;
    }

    public int getExitCode();
}

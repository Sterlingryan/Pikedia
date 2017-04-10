package thesis.uom.pikedia.model;

/**
 * Created by SterlingRyan on 4/9/2017.
 */

public class Line{
    int num;
    String text;
    boolean focus;

    public Line(int num, String text) {
        this.num = num;
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
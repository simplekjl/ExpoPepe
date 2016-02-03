package company.example.expopepe.View;

/**
 * Class to modeling our Images like a POJO
 * Created by admin on 2/3/2016.
 */

public class ViewModel {
    private String text;
    private String image;

    public ViewModel(String text, String image) {
        this. text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}

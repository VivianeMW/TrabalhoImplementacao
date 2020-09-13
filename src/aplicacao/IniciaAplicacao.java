
package aplicacao;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

/**
 *
 * @author Viviane
 */
public class IniciaAplicacao {

    OpenCVFrameConverter.ToMat conversor;

    CascadeClassifier detectaFaces;

    public IniciaAplicacao() throws FrameGrabber.Exception {
        this.conversor = new OpenCVFrameConverter.ToMat();

        this.detectaFaces = new CascadeClassifier("src\\util\\haarcascade_frontalface_alt.xml");

    }

    public OpenCVFrameConverter.ToMat getConversor() {
        return conversor;
    }

    public void setConversor(OpenCVFrameConverter.ToMat conversor) {
        this.conversor = conversor;
    }

    public CascadeClassifier getDetectaFaces() {
        return detectaFaces;
    }

    public void setDetectaFaces(CascadeClassifier detectaFaces) {
        this.detectaFaces = detectaFaces;
    }

}

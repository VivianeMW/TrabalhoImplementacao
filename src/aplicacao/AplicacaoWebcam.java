
package aplicacao;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Viviane
 */
public class AplicacaoWebcam {

    public static void main(String args[]) throws FrameGrabber.Exception {
        OpenCVFrameGrabber webCam;
        IniciaAplicacao inicia = new IniciaAplicacao();
        // o zero significa webcam
        webCam = new OpenCVFrameGrabber(0);
        webCam.start();
        CanvasFrame visualizacao = new CanvasFrame("Visualização", 1);
        String[] nomes = {"", "Vilma", "Viviane"};
        FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
        eigenfaces.read("src\\util\\classificadorEingenFacesAplicacao.yml");
        eigenfaces.setThreshold(9000);
        Frame frameCapturado = null;
        Mat imagem = new Mat();

        while ((frameCapturado = webCam.grab()) != null) {
            imagem = inicia.getConversor().convert(frameCapturado);
            Mat imagemCinza = new Mat();
            opencv_imgproc.cvtColor(imagem, imagemCinza, Imgproc.COLOR_BGRA2GRAY);

            // armazena todas as faces detectadsas na imagem
            RectVector faceEncontrada = new RectVector();
            //nao reconhecer ruidos como faces
            inicia.getDetectaFaces().detectMultiScale(imagemCinza, faceEncontrada, 1.1, 1, 0, new Size(150, 150), new Size(300, 300));

            for (int i = 0; i < faceEncontrada.size(); i++) {
                Rect dFace = faceEncontrada.get(i);
                opencv_imgproc.rectangle(imagem, dFace, new Scalar(0, 0, 255, 0));

                Mat faceCapturada = new Mat(imagemCinza, dFace);
                opencv_imgproc.resize(faceCapturada, faceCapturada, new Size(300, 300));

                IntPointer rotulo = new IntPointer(1);
                DoublePointer confianca = new DoublePointer(1);
                eigenfaces.predict(faceCapturada, rotulo, confianca);
                int predicao = rotulo.get(0);
                String nome = "";
                if (predicao == -1) {
                    nome = "Não encontrado";
                } else {
                    nome = nomes[predicao];
                    //+":"+confianca.get(0);
                    System.out.println(predicao + ":" + confianca.get(0));
                }
                int x = Math.max(dFace.tl().x() - 10, 0);
                int y = Math.max(dFace.tl().y() - 10, 0);
                opencv_imgproc.putText(imagem, nome, new Point(x, y), opencv_imgproc.FONT_HERSHEY_PLAIN, 1.4, new Scalar(0, 0, 255, 0));

            }

            if (visualizacao.isVisible()) {
                visualizacao.showImage(frameCapturado);
            }

        }
        visualizacao.dispose();
        webCam.stop();

    }

}

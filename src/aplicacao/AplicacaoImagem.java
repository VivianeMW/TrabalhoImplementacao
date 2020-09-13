
package aplicacao;

import java.io.IOException;
import java.util.Scanner;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * @author Viviane
 */
public class AplicacaoImagem {

    public static void main(String args[]) throws FrameGrabber.Exception, IOException {
        IniciaAplicacao inicia = new IniciaAplicacao();

        String caminhoArquivo = "";
        do {
            System.out.println("Digite o 1-6 para testar foto de aplicação");
            Scanner ler = new Scanner(System.in);
            int i = ler.nextInt();

            if (i == 1) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\1.1.jpg";

            } else if (i == 2) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\1.2.jpg";
            } else if (i == 3) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\1.3.jpg";
            } else if (i == 4) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\2.1.jpeg";
            } else if (i == 5) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\2.2.jpg";
            } else if (i == 6) {
                caminhoArquivo = "src\\fotos\\aplicacao\\teste\\1.2.jpg";
            } else {
                caminhoArquivo = null;

            }
        } while (caminhoArquivo == null);

        CanvasFrame visualizacao = new CanvasFrame("Visualização", 1);

        Mat imagem = new Mat();
        imagem = opencv_imgcodecs.imread(caminhoArquivo);
        FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
        eigenfaces.read("src\\util\\classificadorEingenFacesAplicacao.yml");
        eigenfaces.setThreshold(60000);
        Frame frame = new Frame();
        opencv_imgproc.resize(imagem, imagem, new Size(300, 300));

        // armazena todas as faces detectadsas na imagem
        RectVector faceEncontrada = new RectVector();
        Mat imagemEscalaCinza = new Mat();
        opencv_imgproc.cvtColor(imagem, imagemEscalaCinza, Imgproc.COLOR_BGRA2GRAY);
        //nao reconhecer ruidos como faces
        inicia.getDetectaFaces().detectMultiScale(imagemEscalaCinza, faceEncontrada, 1.1, 1, 0, new Size(150, 150), new Size(300, 300));
        String[] nomes = {"", "Vilma", "Vivi"};

        for (int j = 0; j < faceEncontrada.size(); j++) {
            Rect dFace = faceEncontrada.get(j);
            opencv_imgproc.rectangle(imagem, dFace, new Scalar(0, 0, 255, 0));

            Mat faceCapturada = new Mat(imagemEscalaCinza, dFace);
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

            int x = Math.max(dFace.tl().x() - 0, 0);
            int y = Math.max(dFace.tl().y() - 10, 0);
            opencv_imgproc.putText(imagem, nome, new Point(x, y), opencv_imgproc.FONT_HERSHEY_PLAIN, 0.8, new Scalar(0, 0, 255, 0));

        }
        frame = inicia.getConversor().convert(imagem);
        visualizacao.showImage(frame);

    }
}

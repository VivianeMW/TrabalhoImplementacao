/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.io.File;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Viviane
 */
public class TesteYale {

    public static void main(String args[]) {

        File diretorio = new File("src\\fotos\\yale\\teste");

        File[] fotosArquivos = diretorio.listFiles();
        MatVector fotos = new MatVector(fotosArquivos.length);

        System.out.println("Imagem teste,nome da class,predição,confiança");
        for (File imagem : fotosArquivos) {
            Mat foto = opencv_imgcodecs.imread(imagem.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

            System.out.print(imagem.getName());
            opencv_imgproc.resize(foto, foto, new Size(300, 300));

            String[] nomes = {"", "Sujeito 1", "Sujeito 2", "Sujeito 3", "Sujeito 4", "Sujeito 5", "Sujeito 6", "Sujeito 7", "Sujeito 8", "Sujeito 9", "Sujeito 10", "Sujeito 11", "Sujeito 12", "Sujeito 13", "Sujeito 14", "Sujeito 15"};
            FaceRecognizer eigenfaces = EigenFaceRecognizer.create();
            eigenfaces.read("src\\util\\classificadorEingenFacesYale.yml");
            eigenfaces.setThreshold(60000);
            IntPointer rotulo = new IntPointer(1);
            DoublePointer confianca = new DoublePointer(1);
            eigenfaces.predict(foto, rotulo, confianca);
            int predicao = rotulo.get(0);
            String nome = "";
            if (predicao == -1) {
                nome = "Não encontrado";
                System.out.println(nome);
            } else {
                nome = nomes[predicao] + "," + confianca.get(0);
                System.out.println("," + nomes[predicao] + "," + predicao + "," + confianca.get(0));
            }

        }
    }

}

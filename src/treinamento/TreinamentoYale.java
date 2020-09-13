/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinamento;

import java.io.File;
import java.nio.IntBuffer;
import org.bytedeco.opencv.global.opencv_core;
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
public class TreinamentoYale {

    public static void main(String args[]) {

        File pasta = new File("src\\fotos\\yale\\treinamento");

        File[] fotosArquivos = pasta.listFiles();
        //matvector é um vetor de imagens
        MatVector fotos = new MatVector(fotosArquivos.length);
        //id para identificação da pessoa
        Mat rotulos = new Mat(fotosArquivos.length, 1, opencv_core.CV_32SC1);
        IntBuffer rotulosBuffer = rotulos.createBuffer();
        int contador = 0;
        for (File imagem : fotosArquivos) {
            Mat foto = opencv_imgcodecs.imread(imagem.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
            int sujeito = Integer.parseInt(imagem.getName().split("\\.")[1]);
            System.out.println(sujeito + " - " + contador);
            opencv_imgproc.resize(foto, foto, new Size(300, 300));
            fotos.put(contador, foto);
            rotulosBuffer.put(contador, sujeito);
            contador++;
        }

        FaceRecognizer eigenfaces = EigenFaceRecognizer.create(100, 2000);
        eigenfaces.train(fotos, rotulos);
        eigenfaces.save("src\\util\\classificadorEingenFacesYale.yml");

    }

}

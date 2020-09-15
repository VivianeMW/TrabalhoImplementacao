# TrabalhoImplementacao
• **Equipe:** Viviane Maria Wehrmeister

• **Problema:** Reconhecimento Facial utilizando Java, com uso de OpenCV e JavaCV. Identificar uma face em uma imagem. Identificar uma face na webcam.

• **Dataset**: Nome do dataset: The Yale Face Database. Disponível em: http://vision.ucsd.edu/datasets/yale_face_dataset_original/yalefaces.zip Acesso em:05/08/2020.
O dataset Yale Faces possui faces de 15 sujeitos, e de cada sujeito 11 imagens. Essas 11 imagens possuem as seguintes condições: Luz central, Óculos, Feliz, Luz à esquerda, Sem óculos, Normal, Luz direita, Triste, Olhos Fechados, Surpreso, Piscada.

• **Técnica:** Utilizando o algoritmo Eigenfaces (PCA principal component analysis): 

O nome eigenfaces dado aos autovetores(eigenvectors) da matriz de covariância das imagens das faces do dataset de faces de treinamento por possuírem aspectos de faces. 
As imagens de treinamento apontam os componentes mais relevantes da face humana.

Criam uma face média com base no valor das imagens do dataset e com a variação dos valores dessas componentes é possível apresentar grande conjunto de faces (dada por autovalores multiplicação escalares). Cada face pode ser representada como combinação linear das diversas eigenfaces.

A classificação de faces, o cálculo da distância entre a imagem sendo analisada e a projetada no novo espaço. Se o valor da distância estiver dentro de uma distância limite (threshold-utiliza algoritmo KNN), é considerada face, caso contrário é considerada como não. 

Imagem: em escala de cinza.
A validação cruzada utilizará Holdout, como segue abaixo:

Treinamento: foi usado 73% do dataset.

Teste: foi usado 27% do dataset.

• **Resultados obtidos:** Na documentação do OpenCV(2020) indica a utilização de 50 Eigenvetores já podem ser suficientes para codificar características faciais importantes, mas confome indicado por Bissi(2018), foi utilizado o numero de 100.
Pra o valor de threshold foi analisado os valores optido nos resultados dos testes afim de se escolher uma valor adequado, não muito grande onde classifique incorretamente as faces, e nem muito baixo onde não consiga reconhecer as faces, pois haverá variação de uma imagem para outra, portanto o valor foi de 9000.
A matriz de confusão, acurácio de 0,84 e também os valores de precision e recall estão no video.

• **Video:** O video é privado precisa estar logado com a conta  fernando.santos@udesc.br, para poder visualizar.
Link do video: https://www.youtube.com/watch?v=boF-Sw-SP7U

Bibliografia

Bissi, T. D. Reconhecimento Facial com os algoritmos Eigenfaces e Fisherfaces, 2018. Dísponível em: https://repositorio.ufu.br/bitstream/123456789/22158/3/ReconhecimentoFacialAlgotimos.pdf.Acesso em:05/08/2020

OpenCv, Face Recogni with OpenCV. 2020. Disponível em: https://docs.opencv.org/2.4/modules/contrib/doc/facerec/facerec_tutorial.html Acesso em : 11/09/2020.



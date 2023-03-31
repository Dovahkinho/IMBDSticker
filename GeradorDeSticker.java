import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeSticker {
    
    
    void criar(InputStream inputStream, String nomeArquivo, BufferedImage joinha, String texto) throws Exception {

        //Leitura da Imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);


        //Cria uma nova imagem(Transp + new size)
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = (int) Math.round(altura * 1.2);
        int alturaExtra = novaAltura - altura;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);


        
        //Copiar a imagem original e sticker pra nova(em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);
        graphics.drawImage(joinha, largura-joinha.getWidth() ,novaAltura-joinha.getHeight() , null);



        //Configurar Fonte
        Font customFont = new Font(Font.SANS_SERIF, Font.BOLD, alturaExtra/2);
        graphics.setColor(Color.WHITE);
        graphics.setFont(customFont);




        //Escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retan = fontMetrics.getStringBounds(texto, graphics);
        var larguraTexto = (int) retan.getWidth();
        var horizontal = ((largura - larguraTexto)/2);
        var vertical = ((altura+(alturaExtra/2)));
        var lineOut = (int)retan.getWidth() /75;
        graphics.drawString(texto, horizontal + lineOut, vertical - lineOut);
        graphics.drawString(texto, horizontal + lineOut, vertical + lineOut);
        graphics.drawString(texto, horizontal - lineOut, vertical - lineOut);
        graphics.drawString(texto, horizontal - lineOut, vertical + lineOut);
        graphics.setColor(Color.BLACK);
        graphics.drawString(texto, horizontal , vertical);


        //Criar em Arquivo!!!
        ImageIO.write(novaImagem, "png", new File("saida/", nomeArquivo));

    }

}
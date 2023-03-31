import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer Conexão HTTP --> Pegar 250 Top filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        //Ler Texto Para Sticker
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String texto= sc.nextLine();

        // Separar Dados Pertinentes(Titulo, Poster, Classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados
        var diretorio = new File("saida/");
        File joia = new File("entrada/adesivo.png");
        diretorio.mkdir();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            String nomeArquivo = titulo + ".png";
            InputStream inputStream = new URL(urlImagem).openStream();
            
            BufferedImage joinha = ImageIO.read(joia);
            var geradora = new GeradorDeSticker();
            geradora.criar(inputStream, nomeArquivo, joinha, texto);

            System.out.println();
            System.out.println(titulo);

            }
        }
    }

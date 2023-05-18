# Simulador de Memória Cache Parametrizável
Este repositório contém um simulador de memória cache desenvolvido em Java usando a IDE do NetBeans. O simulador permite a configuração de vários parâmetros para modelar diferentes cenários de memória cache.

## Funcionalidades
● Implementação de uma memória cache com mapeamento associativo por conjunto (set-associative mapping).<br>
● Possibilidade de configurar os seguintes parâmetros:<br>
>● Tamanho total da cache.<br>
>● Número de vias (associatividade) por conjunto.<br>
>● Política de substituição (por exemplo, LRU - Least Recently Used).<br>
>● Política de escrita (por exemplo, write-through ou write-back).<br>
>● Suporte para operações de leitura e escrita na cache.<br>
● Rastreamento de estatísticas, como taxa de hit, taxa de miss compulsório, taxa de miss por conflito e taxa de miss de capacidade.

## Requisitos do Sistema
● Java Development Kit (JDK) 8 ou superior.
● IDE do NetBeans.

## Como Executar
1. Clone o repositório para o seu ambiente local:
2. Compile o código-fonte Java:
3. Execute o arquivo principal "cache_simulator" fornecendo os argumentos necessários, exemplo:
  3.1 java cache_simulator 256 4 1 R 1 bin_100.bin
4. O simulador de memória cache irá executar e imprimir as estatísticas correspondentes, como taxa de hit, taxa de miss compulsório, taxa de miss por conflito e taxa de miss de capacidade.

## Parâmetros de Entrada

● Tamanho da cache: Especifique o tamanho total da cache em bytes.<br>
● Número de conjuntos: Especifique o número de conjuntos na cache.<br>
● Número de vias: Especifique o número de vias (associatividade) por conjunto.<br>
● Política de substituição: Especifique a política de substituição a ser usada (por exemplo, "LRU" para Least Recently Used).<br>
● Política de escrita: Especifique a política de escrita a ser usada (por exemplo, "R" para write-through ou "B" para write-back).<br>
● Arquivo binário com os dados: Especifique o caminho para o arquivo binário contendo os dados de entrada.<br>

## Saída

O simulador de memória cache irá imprimir a taxa de hit, a taxa de miss compulsório, a taxa de miss por conflito e a taxa de miss de capacidade. Essas informações serão exibidas ao final da execução do simulador e poderão ser usadas para avaliar o desempenho da cache e identificar áreas de melhoria.

● A taxa de hit representa a proporção de vezes em que um dado solicitado é encontrado na cache, evitando assim o acesso à memória principal.<br>
● A taxa de miss compulsório indica a proporção de vezes em que um dado é solicitado pela primeira vez e não está presente na cache, exigindo que seja buscado na memória principal.<br>
● A taxa de miss por conflito mede a proporção de vezes em que dois ou mais dados diferentes mapeiam para o mesmo conjunto da cache, resultando em substituições frequentes de blocos, mesmo quando há espaço livre disponível na cache.<br>
● A taxa de miss de capacidade representa a proporção de vezes em que a capacidade total da cache é insuficiente para armazenar todos os dados necessários. Mesmo que não haja conflito de mapeamento, os blocos podem ser substituídos repetidamente devido à limitação de espaço na cache.<br>

Essas estatísticas serão exibidas ao final da execução do simulador.

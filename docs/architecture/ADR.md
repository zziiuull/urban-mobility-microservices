# ADR 001 - Escolha do Estilo Arquitetural

## Situação

Proposta

## Contexto

O sistema demanda alta disponibilidade e escalabilidade horizontal. Espera-se grande volume de requisições, além de a arquitetura precisar ser tolerante a falhas e flexível para novas funcionalidades.

## Decisão

Optamos por utilizar microsserviços com coreografia como o estilo principal da arquitetura.
Escalabilidade horizontal: Esta abordagem permite separar funcionalidades críticas (corridas, pagamentos, geolocalização) em serviços independentes, facilitando escalar pontos de alta demanda sem impactar o sistema como um todo.​
Baixo acoplamento: A comunicação via eventos permite que serviços evoluam e sejam implantados de forma independente, crucial para equipes paralelas e melhorias contínuas.​
Alta disponibilidade: Microsserviços desacoplados e balanceados via coreografia aumentam a tolerância a falhas, pois a indisponibilidade de um serviço não paralisa o sistema inteiro.​
Descentralização: Não há um orquestrador crítico único, reduzindo ponto único de falha e aumentando a robustez da solução.​

Optamos por utilizar microsserviços com coreografia como o estilo principal da arquitetura, para separar funcionalidades críticas em serviços independentes. A comunicação via eventos permite que serviços evoluam e sejam 
implantados de forma independente e consequentemente aumenta a tolerância a falhas.

## Consequências

Eleva a complexidade de infraestrutura (monitoramento distribuído, logging, rastreabilidade).
Necessita mensageria robusta (Kafka, RabbitMQ)

Aumenta a complexidade de infraestrutura, porém também aumenta a disponibilidade da aplicação. Além disso, eventuais falhas não afetam o fluxo dos outros serviços.

# build
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY ./price-service/ ./price-service/
COPY ./Contracts/ ./Contracts/
RUN dotnet restore ./price-service/price-service.csproj
RUN dotnet publish ./price-service/price-service.csproj -c Release -o /app

# run
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app .
ENV ASPNETCORE_URLS=http://+:8080
EXPOSE 8080
ENTRYPOINT ["dotnet", "price-service.dll"]
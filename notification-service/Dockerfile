# build
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY ./notification-service/ ./notification-service/
COPY ./Contracts/ ./Contracts/
RUN dotnet restore ./notification-service/notification-service.csproj
RUN dotnet publish ./notification-service/notification-service.csproj -c Release -o /app

# run
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app .
ENV ASPNETCORE_URLS=http://+:8080
EXPOSE 8080
ENTRYPOINT ["dotnet", "notification-service.dll"]
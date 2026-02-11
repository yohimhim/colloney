# Colloney

Colloney is a personal finance platform that brings banking, stock trading, budgeting, and market intelligence together into a single application. It was built as a semester project for **COM S 309** at Iowa State University to explore what a unified financial tool could look like when designed from the ground up — one where users don't need to jump between a banking app, a brokerage, a budgeting tool, and a news aggregator.

## Why We Built This

Most people manage their money across a scattered set of apps and services. Checking accounts live in one place, investments in another, budgets in a spreadsheet, and market news in yet another tab. Colloney started from a simple question: what if all of that lived in one place and actually talked to each other?

We wanted to build something where buying a stock automatically reflects in your account balance, where your spending is tracked against budgets you set, and where an AI assistant can look at your financial picture and give you a plain-language summary. The goal was not to replace production financial software, but to build a full-stack system that models how these pieces connect in the real world.

## What It Consists Of

### Banking

Users can open **checking**, **savings**, and **investment** accounts. The system supports deposits, withdrawals, and transfers between accounts with full transaction history. Savings accounts accrue daily interest automatically through a scheduled job.

### Stock Trading

The trading engine connects to the **Finnhub API** for real-time market data. Users can buy and sell stocks from their investment accounts, with the system tracking positions, average cost basis, and realized gains and losses. A WebSocket layer pushes live quote updates to connected clients so prices stay current without polling.

### Budgeting

Users create budgets tied to categories (each with a name, icon, and spending limit) and track their spending against those limits over defined time periods.

### Market News

A news aggregation system pulls financial news from Finnhub on a schedule, covering general market news as well as company-specific stories. News can be filtered by stock symbol, source, or category (general, forex, crypto, merger).

### AI Chat

An integration with **Ollama** gives users access to a local LLM for financial Q&A. Chat history is stored per user, so conversations persist across sessions.

### Social Posts

A community feed lets users create and share posts. The feed updates in real time over WebSockets, so new posts appear for all connected users without a page refresh.

### Company Data

The platform maintains a local store of company metadata — names, logos, and exchange information — synced from Finnhub and used throughout the UI for display.

## Architecture

The backend is a **Spring Boot 3** application written in **Java 17**. It follows a layered architecture where each domain (banking, stock, budgeting, etc.) is organized into its own package with dedicated controllers, services, repositories, and mappers.

Key architectural decisions:

- **MariaDB** for persistent storage with Spring Data JPA
- **WebSockets** for real-time stock quotes and post feeds
- **Scheduled tasks** for daily interest accrual, quote refreshes, and news fetching
- **Spring Security** for authentication and role-based access control (USER and ADMIN roles)
- **OpenAPI / Swagger** for API documentation
- **Prometheus + Loki** for metrics and centralized logging
- **Ansible** for automated deployment to the university server
- **JaCoCo** for code coverage reporting

## Tech Stack

| Layer          | Technology                          |
|----------------|-------------------------------------|
| Framework      | Spring Boot 3.5                     |
| Language       | Java 17                             |
| Database       | MariaDB                             |
| ORM            | Spring Data JPA / Hibernate         |
| Security       | Spring Security                     |
| Real-time      | Jakarta WebSocket                   |
| Market Data    | Finnhub API                         |
| AI             | Ollama (local LLM)                  |
| API Docs       | SpringDoc OpenAPI                   |
| Monitoring     | Micrometer + Prometheus, Loki       |
| Deployment     | Ansible                             |
| Build          | Maven                               |
| Testing        | Spring Boot Test, H2 (in-memory)    |

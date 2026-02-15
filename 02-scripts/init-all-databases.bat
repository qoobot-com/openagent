@echo off
REM PostgreSQL 16 + pgvector 数据库初始化脚本 (Windows)
REM 为所有20个Agent系统初始化数据库

echo ============================================================
echo PostgreSQL 16 + pgvector 数据库初始化
echo ============================================================
echo.

REM 配置
set POSTGRES_HOST=localhost
set POSTGRES_PORT=5432
set POSTGRES_USER=postgres
set POSTGRES_PASSWORD=postgres

REM 等待PostgreSQL就绪
echo 等待PostgreSQL启动...
:wait_loop
pg_isready -h %POSTGRES_HOST% -p %POSTGRES_PORT% -U %POSTGRES_USER% >nul 2>&1
if errorlevel 1 (
    echo PostgreSQL尚未就绪，等待5秒...
    timeout /t 5 /nobreak >nul
    goto wait_loop
)
echo PostgreSQL已就绪
echo.

REM 初始化所有Agent数据库
set count=0
set SCRIPT_DIR=%~dp0

for %%f in ("%SCRIPT_DIR%db-init-*.sql") do (
    if not "%%~nxf"=="db-init-template.sql" (
        echo 初始化: %%~nxf
        psql -h %POSTGRES_HOST% -p %POSTGRES_PORT% -U %POSTGRES_USER% -f "%%f"
        if errorlevel 1 (
            echo ✗ 失败: %%~nxf
        ) else (
            echo ✓ 完成: %%~nxf
        )
        echo.
        set /a count+=1
    )
)

echo ============================================================
echo 数据库初始化完成！
echo 总计初始化了 %count% 个Agent数据库
echo ============================================================

REM 验证安装
echo.
echo 验证PostgreSQL和pgvector版本：
psql -h %POSTGRES_HOST% -p %POSTGRES_PORT% -U %POSTGRES_USER% -d openagent_drug_research_agent -c "SELECT 'PostgreSQL version: ' ^|^| version() AS info UNION ALL SELECT 'pgvector version: ' ^|^| extversion FROM pg_extension WHERE extname = 'vector';"

echo.
echo 所有数据库已准备就绪！

pause

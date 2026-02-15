#!/bin/bash

# PostgreSQL 16 + pgvector 数据库初始化脚本
# 为所有20个Agent系统初始化数据库

set -e

echo "============================================================"
echo "PostgreSQL 16 + pgvector 数据库初始化"
echo "============================================================"
echo ""

# 配置
POSTGRES_HOST="${POSTGRES_HOST:-localhost}"
POSTGRES_PORT="${POSTGRES_PORT:-5432}"
POSTGRES_USER="${POSTGRES_USER:-postgres}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:-postgres}"
POSTGRES_DB="${POSTGRES_DB:-openagent}"

# 等待PostgreSQL就绪
echo "等待PostgreSQL启动..."
until PGPASSWORD=$POSTGRES_PASSWORD psql -h $POSTGRES_HOST -p $POSTGRES_PORT -U $POSTGRES_USER -c '\q' 2>/dev/null; do
  echo "PostgreSQL尚未就绪，等待5秒..."
  sleep 5
done
echo "PostgreSQL已就绪"
echo ""

# 获取脚本所在目录
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 初始化所有Agent数据库
count=0
for sql_file in "$SCRIPT_DIR"/db-init-*.sql; do
  if [ -f "$sql_file" ]; then
    filename=$(basename "$sql_file")
    echo "初始化: $filename"
    PGPASSWORD=$POSTGRES_PASSWORD psql \
      -h $POSTGRES_HOST \
      -p $POSTGRES_PORT \
      -U $POSTGRES_USER \
      -f "$sql_file"
    echo "✓ 完成: $filename"
    echo ""
    count=$((count + 1))
  fi
done

echo "============================================================"
echo "数据库初始化完成！"
echo "总计初始化了 $count 个Agent数据库"
echo "============================================================"

# 验证安装
echo ""
echo "验证PostgreSQL和pgvector版本："
PGPASSWORD=$POSTGRES_PASSWORD psql \
  -h $POSTGRES_HOST \
  -p $POSTGRES_PORT \
  -U $POSTGRES_USER \
  -d openagent_drug_research_agent \
  -c "SELECT 'PostgreSQL version: ' || version() AS info UNION ALL SELECT 'pgvector version: ' || extversion FROM pg_extension WHERE extname = 'vector';"

echo ""
echo "所有数据库已准备就绪！"

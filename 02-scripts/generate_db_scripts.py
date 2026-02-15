"""
为20个Agent系统生成PostgreSQL 16 + pgvector 0.7.4初始化脚本
"""

import os

# Agent系统列表
AGENT_SYSTEMS = [
    {
        "id": "drug-research-agent",
        "name": "Drug Research Agent",
        "description": "药物研发协同AI Agent系统"
    },
    {
        "id": "medical-diagnosis-agent",
        "name": "Medical Diagnosis Agent",
        "description": "智能医疗诊断AI Agent系统"
    },
    {
        "id": "medical-device-agent",
        "name": "Medical Device Agent",
        "description": "医疗设备协同AI Agent系统"
    },
    {
        "id": "agricultural-pest-agent",
        "name": "Agricultural Pest Agent",
        "description": "农业病虫害预测AI Agent系统"
    },
    {
        "id": "cross-border-logistics-agent",
        "name": "Cross Border Logistics Agent",
        "description": "跨境物流优化AI Agent系统"
    },
    {
        "id": "warehouse-scheduling-agent",
        "name": "Warehouse Scheduling Agent",
        "description": "智能仓储调度AI Agent系统"
    },
    {
        "id": "nuclear-maintenance-agent",
        "name": "Nuclear Maintenance Agent",
        "description": "核电设备预测性维护AI Agent系统"
    },
    {
        "id": "power-grid-agent",
        "name": "Power Grid Agent",
        "description": "电网负荷预测AI Agent系统"
    },
    {
        "id": "power-trading-agent",
        "name": "Power Trading Agent",
        "description": "智能电力交易AI Agent系统"
    },
    {
        "id": "semiconductor-optimization-agent",
        "name": "Semiconductor Optimization Agent",
        "description": "半导体工艺优化AI Agent系统"
    },
    {
        "id": "industrial-quality-agent",
        "name": "Industrial Quality Agent",
        "description": "工业质检AI Agent系统"
    },
    {
        "id": "financial-pricing-agent",
        "name": "Financial Pricing Agent",
        "description": "金融动态定价AI Agent系统"
    },
    {
        "id": "investment-advisor-agent",
        "name": "Investment Advisor Agent",
        "description": "智能投资顾问AI Agent系统"
    },
    {
        "id": "construction-safety-agent",
        "name": "Construction Safety Agent",
        "description": "建筑安全巡检AI Agent系统"
    },
    {
        "id": "supply-chain-agent",
        "name": "Supply Chain Agent",
        "description": "供应链韧性AI Agent系统"
    },
    {
        "id": "customer-service-agent",
        "name": "Customer Service Agent",
        "description": "智能客服AI Agent系统"
    },
    {
        "id": "intelligent-traffic-agent",
        "name": "Intelligent Traffic Agent",
        "description": "智能交通管理AI Agent系统"
    },
    {
        "id": "marketing-planning-agent",
        "name": "Marketing Planning Agent",
        "description": "智能营销策划AI Agent系统"
    },
    {
        "id": "intelligent-ops-agent",
        "name": "Intelligent Operations Agent",
        "description": "智能运维管理AI Agent系统"
    }
]


def generate_db_init_script(agent_system):
    """生成数据库初始化脚本"""
    template_file = "d:/05workspaces/openagent/scripts/db-init-template.sql"
    output_file = f"d:/05workspaces/openagent/scripts/db-init-{agent_system['id']}.sql"

    # 读取模板
    with open(template_file, 'r', encoding='utf-8') as f:
        content = f.read()

    # 替换占位符
    content = content.replace('{AGENT_ID}', agent_system['id'].replace('-', '_'))
    content = content.replace('{AGENT_NAME}', agent_system['name'])
    content = content.replace('{AGENT_DESCRIPTION}', agent_system['description'])

    # 写入文件
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(content)

    print(f"✓ Generated: {output_file}")


def main():
    """主函数"""
    print("=" * 60)
    print("生成PostgreSQL 16 + pgvector 0.7.4初始化脚本")
    print("=" * 60)
    print()

    scripts_dir = "d:/05workspaces/openagent/scripts"
    if not os.path.exists(scripts_dir):
        os.makedirs(scripts_dir)
        print(f"✓ Created directory: {scripts_dir}")

    for agent in AGENT_SYSTEMS:
        print(f"Processing: {agent['id']}")
        generate_db_init_script(agent)
        print()

    print("=" * 60)
    print(f"总计生成了 {len(AGENT_SYSTEMS)} 个数据库初始化脚本")
    print("=" * 60)


if __name__ == "__main__":
    main()

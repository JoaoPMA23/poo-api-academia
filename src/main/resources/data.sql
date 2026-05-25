-- =============================================
-- DADOS INICIAIS - Sistema de Academia
-- =============================================

-- Planos
INSERT INTO tb_plano (nome, descricao, duracao_meses, valor, ativo)
VALUES
('Mensal', 'Acesso livre por 1 mês', 1, 89.90, true),
('Trimestral', 'Acesso livre por 3 meses', 3, 239.90, true),
('Semestral', 'Acesso livre por 6 meses', 6, 419.90, true),
('Anual', 'Melhor custo-benefício - 12 meses', 12, 759.90, true);

-- Modalidades
INSERT INTO tb_modalidade (nome, descricao, capacidade_maxima, duracao_minutos, tipo, ativa)
VALUES
('Musculação', 'Treinamento de força com equipamentos', 50, 60, 'MUSCULACAO', true),
('Spinning', 'Aula de ciclismo indoor de alta intensidade', 20, 45, 'CARDIO', true),
('Yoga', 'Prática de postura, respiração e meditação', 15, 60, 'YOGA', true),
('Pilates', 'Fortalecimento do core e flexibilidade', 12, 50, 'PILATES', true),
('Boxe', 'Aula de boxe fitness para condicionamento', 20, 60, 'LUTA', true),
('Zumba', 'Aula de dança fitness animada', 30, 55, 'DANCA', true);

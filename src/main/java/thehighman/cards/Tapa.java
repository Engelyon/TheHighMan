package thehighman.cards;

        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
        import com.megacrit.cardcrawl.actions.common.DamageAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import thehighman.character.TheHighman;
        import thehighman.powers.Chapado;
        import thehighman.powers.Seda_naofunciona;
        import thehighman.util.CardStats;

        /**
         * Representa a carta "Tapa" no jogo.
         * Esta carta é um ataque básico que causa dano a um único inimigo e aplica o efeito "Chapado".
         */
        public class Tapa extends BaseCard {
            // Identificador único da carta
            public static final String ID = makeID("Tapa");

            // Estatísticas da carta, incluindo cor, tipo, raridade, alvo e custo de energia
            private static final CardStats info = new CardStats(
                    TheHighman.Meta.CARD_COLOR, // Cor da carta
                    CardType.ATTACK,            // Tipo: ataque
                    CardRarity.BASIC,           // Raridade: básica
                    CardTarget.ENEMY,           // Alvo: um único inimigo
                    1                           // Custo de energia
            );

            // Dano base da carta
            private static final int DAMAGE = 6;

            // Dano adicional ao aprimorar a carta
            private static final int UPG_DAMAGE = 3;

            /**
             * Construtor da carta "Tapa".
             * Define as propriedades da carta, como dano, palavras-chave e tags.
             */
            public Tapa() {
                super(ID, info);

                // Define o dano base e o dano adicional ao aprimorar
                setDamage(DAMAGE, UPG_DAMAGE);

                // Adiciona tags relacionadas a cartas iniciais e de ataque
                tags.add(CardTags.STARTER_STRIKE);
                tags.add(CardTags.STRIKE);

                // Adiciona a palavra-chave "chapado" para exibição de tooltip
                this.keywords.add("chapado");

                // Inicializa a descrição da carta com os valores dinâmicos (!D!)
                initializeDescription();
            }

            /**
             * Define o comportamento da carta quando utilizada.
             * Causa dano a um inimigo e aplica o efeito "Chapado".
             *
             * @param p O jogador que está utilizando a carta.
             * @param m O monstro alvo.
             */
            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                // Causa dano ao inimigo alvo
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));

                // Aplica o efeito "Chapado" ao inimigo alvo
                addToBot(new ApplyPowerAction(m, p, new Chapado(m, getBonusChapado(1)), getBonusChapado(1)));
            }

            /**
             * Aplica modificadores de poder antes de calcular o dano.
             * Adiciona o número de stacks do poder "Seda" ao dano base.
             */
            @Override
            public void applyPowers() {
                // Obtém o número de stacks do poder "Seda" do jogador
                int sedaStacks = AbstractDungeon.player.hasPower(Seda_naofunciona.POWER_ID) ?
                        AbstractDungeon.player.getPower(Seda_naofunciona.POWER_ID).amount : 0;

                // Adiciona os stacks de "Seda" ao dano base
                this.baseDamage += sedaStacks;

                // Aplica os poderes e recalcula o dano
                super.applyPowers();

                // Remove os stacks de "Seda" do dano base para evitar alterações permanentes
                this.baseDamage -= sedaStacks;

                // Marca o dano como modificado se ele for diferente do dano base
                this.isDamageModified = this.damage != this.baseDamage;
            }

            /**
             * Calcula o bônus do efeito "Chapado" com base no número de stacks do poder "Seda".
             *
             * @param baseBonus O bônus base do efeito "Chapado".
             * @return O bônus total, incluindo os stacks de "Seda".
             */
            private int getBonusChapado(int baseBonus) {
                // Obtém o número de stacks do poder "Seda" do jogador
                int sedaStacks = AbstractDungeon.player.hasPower(Seda_naofunciona.POWER_ID) ?
                        AbstractDungeon.player.getPower(Seda_naofunciona.POWER_ID).amount : 0;

                // Retorna o bônus base somado aos stacks de "Seda"
                return baseBonus + sedaStacks;
            }
        }
package thehighman.cards;

        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
        import com.megacrit.cardcrawl.actions.common.DamageAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import thehighman.character.TheHighman;
        import thehighman.powers.ChapadoPower;
        import thehighman.powers.SedaPower;
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

            private static final int DAMAGE = 5;
            private static final int UPG_DAMAGE = 2;
            private static final int CHAPADO = 1;
            private static final int UPG_CHAPADO = 1;

            public Tapa() {
                super(ID, info);
                // Define o dano base e o dano adicional ao aprimorar
                setDamage(DAMAGE, UPG_DAMAGE);
                setMagic(CHAPADO, UPG_CHAPADO);
                // Adiciona tags relacionadas a cartas iniciais e de ataque
                tags.add(CardTags.STARTER_STRIKE);
                tags.add(CardTags.STRIKE);
                // Adiciona a palavra-chave "chapado" para exibição de tooltip
                this.keywords.add("chapado");
                initializeDescription();
            }
            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                // Causa dano ao inimigo alvo
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));
            }
            @Override
            public void upgrade() {
                if (!upgraded) {
                    upgradeName();
                    upgradeDamage(UPG_DAMAGE); // 6 → 9 de dano
                    initializeDescription();
                }
            }
            //atualiza o magic number com a quantidade de SedaPower
            @Override
            public void applyPowers() {
                super.applyPowers();
                if (AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
                    int seda = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;
                    int bonus = Math.max(0, seda);
                    this.magicNumber = this.baseMagicNumber + bonus;
                    isMagicNumberModified = true;
                } else {
                    this.magicNumber = this.baseMagicNumber;
                    isMagicNumberModified = false;
                }
                initializeDescription();
            }
        }
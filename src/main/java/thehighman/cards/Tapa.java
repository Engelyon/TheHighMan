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

        public class Tapa extends BaseCard {
            public static final String ID = makeID("Tapa");
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
                setDamage(DAMAGE, UPG_DAMAGE);
                setMagic(CHAPADO, UPG_CHAPADO);
                tags.add(CardTags.STARTER_STRIKE);
                tags.add(CardTags.STRIKE);
                initializeDescription();
            }
            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));
            }
            @Override
            public void upgrade() {
                if (!upgraded) {
                    upgradeName();
                    upgradeDamage(UPG_DAMAGE);
                    initializeDescription();
                }
            }

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
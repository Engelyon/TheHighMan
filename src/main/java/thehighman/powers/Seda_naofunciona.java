                package thehighman.powers;

                import com.megacrit.cardcrawl.cards.DamageInfo;
                import com.megacrit.cardcrawl.core.AbstractCreature;
                import com.megacrit.cardcrawl.core.CardCrawlGame;
                import com.megacrit.cardcrawl.powers.AbstractPower;
                import com.megacrit.cardcrawl.localization.PowerStrings;

                import static thehighman.InimigosDoSpire.makeID;

                public class Seda_naofunciona extends AbstractPower {
                    public static final String POWER_ID = makeID("Seda");
                    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

                    // Chapado extra aplicado por cada stack de Seda
                    private static final int CHAPADO_PER_STACK = 1;

                    public Seda_naofunciona(AbstractCreature owner, int amount) {
                        this.name = powerStrings.NAME;
                        this.ID = POWER_ID;
                        this.owner = owner;
                        this.amount = amount;
                        this.type = AbstractPower.PowerType.BUFF;
                        this.isTurnBased = false;
                        updateDescription();
                    }

                    @Override
                    public float atDamageGive(float damage, DamageInfo.DamageType type) {
                        return type == DamageInfo.DamageType.NORMAL ? damage + this.amount : damage;
                    }

                    @Override
                    public void updateDescription() {
                        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
                    }

                    /**
                     * Retorna a quantidade de stacks de Seda que a criatura possui.
                     */
                    public static int getSedaStacks(AbstractCreature creature) {
                        AbstractPower p = creature.getPower(POWER_ID);
                        return p != null ? p.amount : 0;
                    }

                    /**
                     * Retorna o bônus de Chapado a ser aplicado por cartas quando a criatura
                     * possui o poder Seda. Calculado como CHAPADO_PER_STACK * número de stacks.
                     */
                    public static int getChapadoBonus(AbstractCreature creature) {
                        AbstractPower p = creature.getPower(POWER_ID);
                        return p != null ? p.amount * CHAPADO_PER_STACK : 0;
                    }
                }
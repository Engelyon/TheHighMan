package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class FumacaPassiva extends BaseCard {
    public static final String ID = makeID("FumacaPassiva");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int CHAPADO = 1;

    public FumacaPassiva() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        isMultiDamage = true;
        setMagic(CHAPADO);
        this.keywords.add("chapado");
        this.keywords.add("seda");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON));
        addToBot(new VFXAction(new InflameEffect(p)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, magicNumber), magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        applySedaBonus();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        applySedaBonus();
    }

    private void applySedaBonus() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
            int stacks = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;

            // Aplica o bônus de Seda ao dano em área
            for (int i = 0; i < this.multiDamage.length; i++) {
                this.multiDamage[i] += stacks;
            }

            this.damage += stacks;
            this.isDamageModified = true;
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 4 → 6 de dano base
            initializeDescription();
        }
    }
}
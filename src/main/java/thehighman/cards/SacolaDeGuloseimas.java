package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class SacolaDeGuloseimas extends BaseCard {
    public static final String ID = makeID("SacolaDeGuloseimas");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 0; // mantemos dano base; ‘upgrade’ dá proteção
    private static final int BONUS_DAMAGE = 5;
    private static final int UPG_BLOCK = 5;

    public SacolaDeGuloseimas() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(0, UPG_BLOCK);
        this.isMultiDamage = true;
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, 1), 1));
        if (m != null && m.hasPower(LaricaPower.POWER_ID)) {
            addToBot(new DamageAllEnemiesAction(p,
                    DamageInfo.createDamageMatrix(BONUS_DAMAGE, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.FIRE));
        }
        if (upgraded) {
            addToBot(new GainBlockAction(p, p, UPG_BLOCK));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}
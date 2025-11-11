package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class Fissura extends BaseCard {
    public static final String ID = makeID("Fissura");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK_GAIN = 3;

    public Fissura() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BLOCK_GAIN, BLOCK_GAIN + 2); // 3 → 5 de Bloqueio com upgrade

        this.keywords.add("erva");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // Se tiver Erva, consome 1 e ganha Bloqueio
        if (p.hasPower(ErvaPower.POWER_ID) && p.getPower(ErvaPower.POWER_ID).amount >= 1) {
            addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, 1));
            addToBot(new GainBlockAction(p, BLOCK_GAIN));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);     // 7 → 10 de dano
            upgradeMagicNumber(2);         // 3 → 5 de Bloqueio
            initializeDescription();
        }
    }
}
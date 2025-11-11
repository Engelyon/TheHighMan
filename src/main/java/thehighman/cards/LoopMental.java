package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.actions.DrawCheckBadTripAction;
import thehighman.util.CardStats;

public class LoopMental extends BaseCard {
    public static final String ID = makeID("LoopMental");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int BASE_DRAW = 2; // base: compra 2
    private static final int UPG_DRAW = 3; // up: compra 3
    private static final int BONUS_DRAW_ON_BADTRIP = 2; // se desenhar >=1 BadTrip, compra +2 (base)
    private static final int UPG_BONUS_DRAW_ON_BADTRIP = 3; // up: compra +3

    public LoopMental() {
        super(ID, info);
        setDamage(DAMAGE);
        this.keywords.add("bad trip");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // dano
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        // determina draws conforme upgrade
        int draw = upgraded ? UPG_DRAW : BASE_DRAW;
        int extraIfBadTrip = upgraded ? UPG_BONUS_DRAW_ON_BADTRIP : BONUS_DRAW_ON_BADTRIP;

        // usa o action que desenha e verifica BadTrip entre as cartas desenhadas
        addToBot(new DrawCheckBadTripAction(p, draw, extraIfBadTrip, makeID("BadTrip")));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
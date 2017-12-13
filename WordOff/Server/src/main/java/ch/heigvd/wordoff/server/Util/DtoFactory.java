package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Dto.Game.*;
import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.RackDto;
import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.*;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.User.*;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.Rack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DtoFactory {
    private static ModelMapper modelMapper = configuredModelMapper();

    private static ModelMapper configuredModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Tile, ITile> toTileDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), TileDto.class);
        Converter<Slot, ISlot> toSlotDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), SlotDto.class);
        Converter<Rack, IRack> toRackDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), RackDto.class);
        Converter<Player, PlayerDto> toPlayerDto =
                ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), PlayerDto.class);

        //
        // Tiles
        //
        TypeMap<Tile, ITile> tileMap = modelMapper.createTypeMap(Tile.class, ITile.class);
        tileMap.setProvider(req -> new TileDto());

        tileMap.include(Tile.class, TileDto.class);


        //
        // Racks
        //
        TypeMap<Rack, IRack> rackMap = modelMapper.createTypeMap(Rack.class, IRack.class)
                .include(SwapRack.class, IRack.class)
                .include(PlayerRack.class, IRack.class)
                .include(SwapRack.class, RackDto.class)
                .include(PlayerRack.class, RackDto.class);
        modelMapper.typeMap(SwapRack.class, IRack.class).setProvider(req -> new SwapRackDto());
        modelMapper.typeMap(PlayerRack.class, IRack.class).setProvider(req -> new PlayerRackDto());
        modelMapper.typeMap(SwapRack.class, RackDto.class).setProvider(req -> new SwapRackDto());
        modelMapper.typeMap(PlayerRack.class, RackDto.class).setProvider(req -> new PlayerRackDto());

        //
        // Slots
        //
        TypeMap<Slot, ISlot> slotMap = modelMapper.createTypeMap(Slot.class, ISlot.class);
        slotMap.addMappings(mapper -> mapper.using(toTileDto).map(Slot::getTile, ISlot::setTile));

        slotMap.include(SwapSlot.class, ISlot.class)
                .include(L2Slot.class, ISlot.class)
                .include(L3Slot.class, ISlot.class)
                .include(LastSlot.class, ISlot.class)
                .include(Slot.class, SlotDto.class)
                .include(SwapSlot.class, SlotDto.class)
                .include(L2Slot.class, SlotDto.class)
                .include(L3Slot.class, SlotDto.class)
                .include(LastSlot.class, SlotDto.class);

        modelMapper.typeMap(SwapSlot.class, SlotDto.class).setProvider(req -> new SwapSlotDto());
        modelMapper.typeMap(L2Slot.class, SlotDto.class).setProvider(req -> new L2SlotDto());
        modelMapper.typeMap(L3Slot.class, SlotDto.class).setProvider(req -> new L3SlotDto());
        modelMapper.typeMap(LastSlot.class, SlotDto.class).setProvider(req -> new LastSlotDto());
        modelMapper.typeMap(Slot.class, ISlot.class).setProvider(req -> new SlotDto());
        modelMapper.typeMap(SwapSlot.class, ISlot.class).setProvider(req -> new SwapSlotDto());
        modelMapper.typeMap(L2Slot.class, ISlot.class).setProvider(req -> new L2SlotDto());
        modelMapper.typeMap(L3Slot.class, ISlot.class).setProvider(req -> new L3SlotDto());
        modelMapper.typeMap(LastSlot.class, ISlot.class).setProvider(req -> new LastSlotDto());

        //
        // Players
        //
        TypeMap<Player, PlayerDto> playerMap = modelMapper.createTypeMap(Player.class, PlayerDto.class)
                .include(User.class, PlayerDto.class)
                .include(Ai.class, PlayerDto.class);

        modelMapper.typeMap(User.class, PlayerDto.class).setProvider(req -> new UserSummaryDto());

        //
        // Sides
        //
        TypeMap<Side, OtherSideDto> sideMap = modelMapper.createTypeMap(Side.class, OtherSideDto.class);
        sideMap.addMappings(mapper -> mapper.using(toPlayerDto).map(Side::getPlayer, OtherSideDto::setPlayer));
        sideMap.include(Side.class, SideDto.class);

        modelMapper.typeMap(Side.class, SideDto.class).addMapping(Side::getPlayerRack, SideDto::setPlayerRack);

        //
        // Games
        //
        Converter<Bag, Integer> bagConverter = ctx -> ctx.getSource().getTiles().size();

        TypeMap<Game, GameDto> gameMap = modelMapper.createTypeMap(Game.class, GameDto.class);
        gameMap.addMappings(mapper -> mapper.using(bagConverter).map(Game::getBag, GameDto::setBagSize));

        //
        // Relations
        //
        TypeMap<Relation, RelationDto> relationMap = modelMapper.createTypeMap(Relation.class, RelationDto.class);
        relationMap.addMappings(mapper -> mapper
                .using(getUrlConverter("/me/relations"))
                .map(r -> r.getTarget().getId(), RelationDto::setEndpoint));

        //
        // Invitations
        //
        TypeMap<Invitation, InvitationDto> invitationMap = modelMapper.createTypeMap(Invitation.class, InvitationDto.class);
        invitationMap.addMappings(mapper -> mapper
                .using(getUrlConverter("/me/invitations"))
                .map(i -> i.getMode().getId(), InvitationDto::setEndpoint));

        return modelMapper;
    }

    private static Converter<Long, String> getUrlConverter(String resource) {
        return ctx -> resource + "/" + ctx.getSource();
    }

    public static TileDto createFrom(Tile entity) {
        return modelMapper.map(entity, TileDto.class);
    }

    public static SlotDto createFrom(Slot entity) {
        return modelMapper.map(entity, SlotDto.class);
    }

    public static RackDto createFrom(Rack entity) {
        return modelMapper.map(entity, RackDto.class);
    }

    public static ChallengeDto createFrom(Challenge entity) {
        return modelMapper.map(entity, ChallengeDto.class);
    }

    public static UserSummaryDto createSummaryFrom(User entity) {
        return modelMapper.map(entity, UserSummaryDto.class);
    }

    public static UserDto createFrom(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public static PlayerDto createFrom(Player entity) {
        return modelMapper.map(entity, PlayerDto.class);
    }

    public static SideDto createFrom(Side entity) {
        return modelMapper.map(entity, SideDto.class);
    }

    public static OtherSideDto createOtherFrom(Side entity) {
        return modelMapper.map(entity, OtherSideDto.class);
    }

    public static GameDto createFrom(Game entity, User viewer) {
        Side mySide = entity.getSideOfPlayer(viewer);
        Side otherSide = entity.getSideOfPlayer(entity.getOtherPlayer(viewer));

        GameDto dto = modelMapper.map(entity, GameDto.class);
        dto.setMySide(modelMapper.map(
                mySide, SideDto.class));
        dto.setOtherSide(modelMapper.map(
                otherSide, OtherSideDto.class));
        dto.setMyTurn(Objects.equals(entity.getCurrPlayer().getId(), viewer.getId()));

        // If it's the viewer turn, then he sees the last completed challenge (if it exists) of his
        // adversary.
        List<Answer> otherAnswers = otherSide.getAnswers();
        if(dto.isMyTurn() && !otherAnswers.isEmpty()) {
            Challenge lastChallenge = otherAnswers.get(otherAnswers.size() - 1).getChallenge();
            dto.getOtherSide().setChallenge(modelMapper.map(lastChallenge, ChallengeDto.class));
        }

        return dto;
    }

    public static GameSummaryDto createSummaryFrom(Game entity, User viewer) {
        GameSummaryDto dto = modelMapper.map(entity, GameSummaryDto.class);
        dto.setOtherPlayer(modelMapper.map(entity.getOtherPlayer(viewer), PlayerDto.class));
        return dto;
    }
    
    public static MeDto createMeFrom(User entity) {
        return modelMapper.map(entity, MeDto.class);
    }

    public static RelationDto createFrom(Relation entity) {
        return modelMapper.map(entity, RelationDto.class);
    }

    public static RelatedUserSummaryDto createRelatedSummaryFrom(User entity, User viewer) {
        RelatedUserSummaryDto dto = modelMapper.map(entity, RelatedUserSummaryDto.class);
        dto.setRelation(createFrom(viewer.getRelation(entity)));
        return dto;
    }

    public static InvitationDto createFrom(Invitation entity) {
        return modelMapper.map(entity, InvitationDto.class);
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}